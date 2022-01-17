using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using UDT.Business.Interfaces;
using UDT.Model.Entities;
using UDT.Model.ViewModels;
using UDT.Model.Mappers;
using System.Linq;
using UDT.Model;

namespace UniversityDeadlineTracker.Controllers
{
    [ApiController]
    [Route("api/account")]
    public class AccountController : ControllerBase
    {

        private IAccountService _accountService;

        public AccountController(IAccountService accountService)
        {
            _accountService = accountService;
        }

        [HttpPost]
        [Route("login")]
        [Produces("application/json")]
        public IActionResult Login([FromBody] AuthenticationRequest login)
        {
            IActionResult response = Unauthorized();
            var data = _accountService.Authenticate(login.Username, login.Password);
            if (data.Token != null && data.User != null)
                response = Ok(data);

            return response;
        }

    }
}