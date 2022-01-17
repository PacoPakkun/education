using System.Collections.Generic;
using System.Threading.Tasks;
using UDT.Model.Entities;

namespace UDT.Business.Interfaces
{
    public interface IUserTaskService
    {
        IAsyncEnumerable<UserTask> GetAllAsync();

        Task<UserTask> GetByIdAsync(int id);

        Task<UserTask> AddAsync(UserTask userTask);

        Task<bool> DeleteAsync(int id);

        Task<UserTask> UpdateAsync(UserTask userTask);
    }
}