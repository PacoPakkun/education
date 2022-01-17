using System;
using System.Collections.Generic;
using System.Text;
using UDT.Repository;
using UDT.Model.Entities;
using UDT.Model.ViewModels;
using UDT.Model.Mappers;
using System.Threading.Tasks;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace UDT.Business.Task
{
    public class ServiceTask : IServiceTask
    {
        private readonly DataContext _dbContext;

        public ServiceTask(DataContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<Model.Entities.Task> AddTask(TaskCreationViewModel taskDto)
        {
            var task = TaskMappers.toEntity(taskDto);
            await _dbContext.Tasks.AddAsync(task);
            await _dbContext.SaveChangesAsync();
            return task;
        }

        public async System.Threading.Tasks.Task DeleteTask(int taskId)
        {
            var task = _dbContext.Tasks.Where(task => task.Id == taskId).Single();
            _dbContext.Tasks.Remove(task);
            await _dbContext.SaveChangesAsync();
        }

        public async Task<UDT.Model.Entities.Task> EditTask(TaskUpdateViewModel taskDto)
        {
            var task = TaskMappers.toEntity(taskDto);
            _dbContext.Tasks.Update(task);
            await _dbContext.SaveChangesAsync();
            return task;
        }

        public IAsyncEnumerable<Model.Entities.Task> GetAll()
        {
            return _dbContext.Tasks.AsAsyncEnumerable();
        }

        public async Task<Model.Entities.Task> GetById(int taskId)
        {
            return await _dbContext.Tasks.FirstOrDefaultAsync(task => task.Id == taskId);
        }
    }
}
