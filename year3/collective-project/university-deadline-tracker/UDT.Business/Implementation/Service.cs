using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using System.Threading.Tasks;
using UDT.Business.Interfaces;
using UDT.Model.Entities;
using UDT.Repository;
using UDT.Business.Task;
using Microsoft.EntityFrameworkCore;

namespace UDT.Business.Implementation
{
    public class Service : IService
    {
        private IUserService _userService;
        private IServiceTask _taskService;
        private IUserTaskService _userTaskService;
        private ISubjectService _subjectService;
        private readonly DataContext _context;

        public Service(IUserService userService, IServiceTask taskService, IUserTaskService userTaskService,
            ISubjectService subjectService, DataContext context)
        {
            _userService = userService;
            _taskService = taskService;
            _userTaskService = userTaskService;
            _subjectService = subjectService;
            _context = context;
        }

        public async Task<User> enrollUserToSubject(int userId, int subjectId)
        {
            User user = await _userService.getByIdWithSubject(userId);
            if (user == null)
                throw new Exception("The user does not exist!");
            Subject subject = await _subjectService.GetByIdAsync(subjectId);
            if (subject == null)
                throw new Exception("The subject does not exist!");
            if (user.Subjects == null)
                user.Subjects = new List<Subject>();
            if (user.Subjects.Contains(subject))
                throw new Exception("The user already has this subject!");
            user.Subjects.Add(subject);
            await _context.SaveChangesAsync();
            return user;
        }

        public async Task<IEnumerable<UserTask>> getUserTasksForUser(int user)
        {
            return _context.UsersTasks.Where(us => us.UserId == user).AsEnumerable();
        }
    }
}