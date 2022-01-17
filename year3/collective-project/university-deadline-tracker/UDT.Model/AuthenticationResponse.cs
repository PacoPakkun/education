using UDT.Model.ViewModels;

namespace UDT.Model
{
    public class AuthenticationResponse
    {
        
        public string Token { get; set; }

        public UserViewModel User { get; set; }
    }
}