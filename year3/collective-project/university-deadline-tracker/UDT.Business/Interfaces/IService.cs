using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using UDT.Model.Entities;

namespace UDT.Business.Interfaces
{
    public interface IService
    {
        Task<User> enrollUserToSubject(int userId, int subjectId);
        Task<IEnumerable<UDT.Model.Entities.UserTask>> getUserTasksForUser(int userId);

    }
}
