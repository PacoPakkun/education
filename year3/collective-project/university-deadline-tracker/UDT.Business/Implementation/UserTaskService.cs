using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using UDT.Business.Interfaces;
using UDT.Model.Entities;
using UDT.Repository;

namespace UDT.Business.Implementation
{
    public class UserTaskService : IUserTaskService
    {
        private readonly DataContext _dataContext;

        public UserTaskService(DataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public IAsyncEnumerable<UserTask> GetAllAsync()
        {
            return _dataContext.UsersTasks.AsAsyncEnumerable();
        }

        public async Task<UserTask> GetByIdAsync(int id)
        {
            return await _dataContext.UsersTasks.FirstOrDefaultAsync(userTask => userTask.Id == id);
        }

        public async Task<UserTask> AddAsync(UserTask userTask)
        {
            await _dataContext.UsersTasks.AddAsync(userTask);
            await _dataContext.SaveChangesAsync();

            return userTask;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var existingUserTask = await _dataContext.UsersTasks
                .FirstOrDefaultAsync(userTask => userTask.Id == id);

            if (existingUserTask == null) return false;

            _dataContext.UsersTasks.Remove(existingUserTask);
            await _dataContext.SaveChangesAsync();

            return true;
        }

        public async Task<UserTask> UpdateAsync(UserTask updatedUserTask)
        {
            var existingUserTask = await _dataContext.UsersTasks
                .FirstOrDefaultAsync(userTask => userTask.Id == updatedUserTask.Id);

            if (existingUserTask == null) return null;
            
            _dataContext.Entry(existingUserTask).CurrentValues.SetValues(updatedUserTask);
            await _dataContext.SaveChangesAsync();

            return updatedUserTask;
        }
    }
}