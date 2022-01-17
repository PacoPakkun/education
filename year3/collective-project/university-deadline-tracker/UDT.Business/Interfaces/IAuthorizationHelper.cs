
using System.Threading.Tasks;
using UDT.Model.Entities;

namespace UDT.Business.Interfaces
{
    public interface IAuthorizationHelper
    {
        bool IsTokenValid(string token);
        Task<bool> IsUsersRoleAuthorized(string token, string authorizedRoles);
    }
}
