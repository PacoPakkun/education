using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    class MeciValidator : Validator<Meci>
    {
        public void Validate(Meci m)
        {
            bool valid = true;
            if (m.ID <= 0 || m.data == null || m.echipa1 == null || m.echipa2 == null)
                valid = false;
            if (valid == false)
            {
                throw new ValidationException("Obiectul nu e valid");
            }
        }
    }
}
