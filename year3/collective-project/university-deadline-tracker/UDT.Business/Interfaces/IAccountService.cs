using System;
using System.Collections.Generic;
using System.Text;
using UDT.Model;

namespace UDT.Business.Interfaces
{
    public interface IAccountService
    {
        UDT.Model.AuthenticationResponse Authenticate(string username, string password);
    }
}
