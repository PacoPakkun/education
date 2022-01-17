using SwimModel.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Services
{
    public interface Observer
    {
        void Update(Inscriere inscriere);
    }
}
