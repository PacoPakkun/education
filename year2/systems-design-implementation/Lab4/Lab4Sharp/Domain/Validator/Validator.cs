using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain.Validator
{
    interface Validator<E>
    {
        void validate(E entity);
    }
}
