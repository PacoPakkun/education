using SwimModel.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimNetwork
{
	public interface Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}

	[Serializable]
	public class GetProbeResponse : Response
	{
		private Proba[] probe;

		public GetProbeResponse(Proba[] probe)
		{
			this.probe = probe;
		}

		public virtual Proba[] Probe
		{
			get
			{
				return probe;
			}
		}
	}

	[Serializable]
	public class FindProbaResponse : Response
	{
		private Proba proba;

		public FindProbaResponse(Proba proba)
		{
			this.proba = proba;
		}

		public virtual Proba Proba
		{
			get
			{
				return proba;
			}
		}
	}

	[Serializable]
	public class GetInscrisiResponse : Response
	{
		private Participant[] participants;

		public GetInscrisiResponse(Participant[] participants)
		{
			this.participants = participants;
		}

		public virtual Participant[] Participants
		{
			get
			{
				return participants;
			}
		}
	}

	[Serializable]
	public class GetInscrieriResponse : Response
	{
		private Proba[] probe;

		public GetInscrieriResponse(Proba[] probe)
		{
			this.probe = probe;
		}

		public virtual Proba[] Probe
		{
			get
			{
				return probe;
			}
		}
	}
	[Serializable]
	public class GetNrParticipantiResponse : Response
	{
		private int nr;

		public GetNrParticipantiResponse(int nr)
		{
			this.nr = nr;
		}

		public virtual int Nr
		{
			get
			{
				return nr;
			}
		}
	}

	[Serializable]
	public class AddParticipantResponse : Response
	{
		private Participant participant;

		public AddParticipantResponse(Participant participant)
		{
			this.participant = participant;
		}

		public virtual Participant Participant
		{
			get
			{
				return participant;
			}
		}
	}

	//

	public interface UpdateResponse : Response
	{
	}

	[Serializable]
	public class RegisterResponse : UpdateResponse
	{
		private Inscriere inscriere;

		public RegisterResponse(Inscriere inscriere)
		{
			this.inscriere = inscriere;
		}

		public virtual Inscriere Inscriere
		{
			get
			{
				return inscriere;
			}
		}
	}
}
