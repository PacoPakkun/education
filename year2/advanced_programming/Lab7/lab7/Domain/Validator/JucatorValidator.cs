using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    class JucatorValidator : Validator<Jucator>
    {
        public void Validate(Jucator j)
        {
            bool valid = true;
            if (j.ID <= 0 || j.nume == "" || j.scoala == "" || j.echipa == null)
                valid = false;
            if (valid == false)
            {
                throw new ValidationException("Obiectul nu e valid");
            }
        }
    }
}
