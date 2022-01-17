using SwimModel.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimNetwork
{
	public interface Request
	{
	}


	[Serializable]
	public class LoginRequest : Request
	{
		private User user;

		public LoginRequest(User user)
		{
			this.user = user;
		}

		public virtual User User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class LogoutRequest : Request
	{
		private User user;

		public LogoutRequest(User user)
		{
			this.user = user;
		}

		public virtual User User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class GetProbeRequest : Request
	{
		public GetProbeRequest()
		{
		}
	}

	[Serializable]
	public class FindProbaRequest : Request
	{
		private int id;

		public FindProbaRequest(int id)
		{
			this.id = id;
		}

		public virtual int Id
		{
			get
			{
				return id;
			}
		}
	}

	[Serializable]
	public class GetInscrisiRequest : Request
	{
		private Proba proba;

		public GetInscrisiRequest(Proba proba)
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
	public class GetInscrieriRequest : Request
	{
		private Participant participant;

		public GetInscrieriRequest(Participant participant)
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
	[Serializable]
	public class GetNrParticipantiRequest : Request
	{
		private Proba proba;

		public GetNrParticipantiRequest(Proba proba)
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
	public class AddParticipantRequest : Request
	{
		private Participant participant;

		public AddParticipantRequest(Participant participant)
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
	[Serializable]
	public class AddInscriereRequest : Request
	{
		private Inscriere inscriere;
		private User user;

		public AddInscriereRequest(Inscriere inscriere, User user)
		{
			this.inscriere = inscriere;
			this.user = user;
		}

		public virtual Inscriere Inscriere
		{
			get
			{
				return inscriere;
			}
		}
		public virtual User User
		{
			get
			{
				return user;
			}
		}
	}
}
