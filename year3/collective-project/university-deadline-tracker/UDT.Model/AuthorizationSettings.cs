using System;
using System.Collections.Generic;
using System.Text;

namespace UDT.Model
{
    public class AuthorizationSettings
    {
        public string Secret { get; set; }
        public string Issuer { get; set; }
        public string Audience { get; set; }
    }
}
