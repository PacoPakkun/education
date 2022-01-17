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
    public class ClientWorker : Observer //, Runnable
    {
        private IService server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ClientWorker(IService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request) request);
                    if (response != null)
                    {
                        sendResponse((Response) response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        public virtual void Update(Inscriere inscriere)
        {
            Console.WriteLine("Registration received  " + inscriere);
            try
            {
                Task.Run(() => sendResponse(new RegisterResponse(inscriere)));
            }
            catch (Exception e)
            {
                throw new Exception("Sending error: " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest) request;
                try
                {
                    lock (server)
                    {
                        server.Login(logReq.User, this);
                    }

                    return new OkResponse();
                }
                catch (Exception e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest) request;
                try
                {
                    lock (server)
                    {
                        server.Logout(logReq.User);
                    }

                    connected = false;
                    return new OkResponse();
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetProbeRequest)
            {
                Console.WriteLine("GetProbeRequest Request ...");
                GetProbeRequest getReq = (GetProbeRequest) request;
                try
                {
                    Proba[] probe;
                    lock (server)
                    {
                        probe = server.FindAllProbe().ToArray();
                    }

                    return new GetProbeResponse(probe);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is FindProbaRequest)
            {
                Console.WriteLine("FindProbaRequest Request ...");
                FindProbaRequest getReq = (FindProbaRequest) request;
                try
                {
                    Proba proba;
                    lock (server)
                    {
                        proba = server.FindProba(getReq.Id);
                    }

                    return new FindProbaResponse(proba);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetInscrisiRequest)
            {
                Console.WriteLine("GetInscrisiRequest Request ...");
                GetInscrisiRequest getReq = (GetInscrisiRequest) request;
                try
                {
                    Participant[] participants;
                    lock (server)
                    {
                        participants = server.GetInscrisi(getReq.Proba).ToArray();
                    }

                    return new GetInscrisiResponse(participants);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetInscrieriRequest)
            {
                Console.WriteLine("GetInscrieriRequest Request ...");
                GetInscrieriRequest getReq = (GetInscrieriRequest) request;
                try
                {
                    Proba[] probe;
                    lock (server)
                    {
                        probe = server.GetInscrieri(getReq.Participant).ToArray();
                    }

                    return new GetInscrieriResponse(probe);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetNrParticipantiRequest)
            {
                Console.WriteLine("GetNrParticipantiRequest Request ...");
                GetNrParticipantiRequest getReq = (GetNrParticipantiRequest) request;
                try
                {
                    int nr;
                    lock (server)
                    {
                        nr = server.NrParticipanti(getReq.Proba);
                    }

                    return new GetNrParticipantiResponse(nr);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is AddParticipantRequest)
            {
                Console.WriteLine("AddParticipantRequest Request ...");
                AddParticipantRequest getReq = (AddParticipantRequest) request;
                try
                {
                    Participant participant;
                    lock (server)
                    {
                        participant = server.AddParticipant(getReq.Participant);
                    }

                    return new AddParticipantResponse(participant);
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is AddInscriereRequest)
            {
                Console.WriteLine("AddInscriereRequest Request ...");
                AddInscriereRequest getReq = (AddInscriereRequest) request;
                try
                {
                    Inscriere inscriere;
                    lock (server)
                    {
                        inscriere = server.AddInscriere(getReq.Inscriere, getReq.User);
                    }
                    return new OkResponse();
                }
                catch (Exception e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }
    }
}