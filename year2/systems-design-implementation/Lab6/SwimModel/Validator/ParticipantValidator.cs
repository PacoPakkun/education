using SwimModel.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Validator
{
    public class ParticipantValidator : Validator<Participant>
    {
        public void validate(Participant participant)
        {
            if (participant.nume == "")
            {
                throw new Exception("Nume invalid");
            }
            if (participant.varsta <= 0)
            {
                throw new Exception("Varsta invalida");
            }
        }
    }
}
