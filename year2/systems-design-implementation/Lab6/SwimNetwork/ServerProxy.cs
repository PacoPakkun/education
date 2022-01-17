using SwimModel.Domain;
using SwimModel.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace SwimNetwork
{
	public class ServerProxy : IService
	{
		private string host;
		private int port;

		private Observer client;

		private NetworkStream stream;

		private IFormatter formatter;
		private TcpClient connection;

		private Queue<Response> responses;
		private volatile bool finished;
		private EventWaitHandle _waitHandle;
		public ServerProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<Response>();
		}

		//

		public virtual void Login(User user, Observer client)
		{
			initializeConnection();
			sendRequest(new LoginRequest(user));
			Response response = readResponse();
			if (response is OkResponse)
			{
				this.client = client;
				return;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				closeConnection();
				throw new Exception(err.Message);
			}
		}
		public virtual void Logout(User user)
		{
			sendRequest(new LogoutRequest(user));
			Response response = readResponse();
			closeConnection();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
		}

		public IEnumerable<Proba> FindAllProbe()
        {
			sendRequest(new GetProbeRequest());
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			GetProbeResponse resp = (GetProbeResponse)response;
			return resp.Probe;
		}

		public Proba FindProba(int id)
        {
			sendRequest(new FindProbaRequest(id));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			FindProbaResponse resp = (FindProbaResponse)response;
			return resp.Proba;
		}

		public Participant AddParticipant(Participant participant)
		{
			sendRequest(new AddParticipantRequest(participant));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			AddParticipantResponse resp = (AddParticipantResponse)response;
			return resp.Participant;
		}

		public Inscriere AddInscriere(Inscriere inscriere, User user)
		{
			sendRequest(new AddInscriereRequest(inscriere, user));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}

			return new Inscriere(-1,-1);
			// RegisterResponse resp = (RegisterResponse)response;
			// return resp.Inscriere;
		}

		public int NrParticipanti(Proba proba)
		{
			sendRequest(new GetNrParticipantiRequest(proba));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			GetNrParticipantiResponse resp = (GetNrParticipantiResponse)response;
			return resp.Nr;
		}

		public IEnumerable<Participant> GetInscrisi(Proba proba)
		{
			sendRequest(new GetInscrisiRequest(proba));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			GetInscrisiResponse resp = (GetInscrisiResponse)response;
			return resp.Participants;
		}

		public IEnumerable<Proba> GetInscrieri(Participant participant)
		{
			sendRequest(new GetInscrieriRequest(participant));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new Exception(err.Message);
			}
			GetInscrieriResponse resp = (GetInscrieriResponse)response;
			return resp.Probe;
		}

		public void handleUpdate(UpdateResponse update)
		{
			Inscriere inscriere = ((RegisterResponse)update).Inscriere;
			Console.WriteLine("Registration complete" + inscriere);
			try
			{
				client.Update(inscriere);
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		//

		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{
				throw new Exception("Error sending object " + e);
			}

		}

		private Response readResponse()
		{
			Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}

		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}

		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is UpdateResponse)
					{
						Task.Run(()=>
							handleUpdate((UpdateResponse)response));
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((Response)response);

						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}
	}
}
