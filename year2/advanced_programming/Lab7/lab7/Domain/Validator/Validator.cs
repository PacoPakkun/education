using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    interface Validator<E>
    {
        void Validate(E e);
    }
}
