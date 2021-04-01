using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain.Validator
{
    class ValidationException : ApplicationException
    {
        public ValidationException(String message) : base(message)
        {
        }
    }
}
