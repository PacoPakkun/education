using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    class JucatorActivValidator : Validator<JucatorActiv>
    {
        public void Validate(JucatorActiv j)
        {
            bool valid = true;
            if (j.ID.Item1 <= 0 || j.ID.Item2 <= 0 || j.nrPuncte < 0)
                valid = false;
            if (valid == false)
            {
                throw new ValidationException("Obiectul nu e valid");
            }
        }
    }
}
