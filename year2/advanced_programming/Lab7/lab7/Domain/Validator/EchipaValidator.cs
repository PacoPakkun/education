using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    class EchipaValidator : Validator<Echipa>
    {
        public void Validate(Echipa e)
        {
            bool valid = true;
            if (e.ID <= 0 || e.nume == "")
                valid = false;
            if (valid == false)
            {
                throw new ValidationException("Obiectul nu e valid");
            }
        }
    }
}
