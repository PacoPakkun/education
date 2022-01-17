using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using UDT.Business.Interfaces;
using UDT.Model.Entities;
using UDT.Repository;

namespace UDT.Business.Implementation
{
    public class SubjectService : ISubjectService
    {
        private readonly DataContext _dataContext;

        public SubjectService(DataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public IAsyncEnumerable<Subject> GetAllAsync()
        {
            return _dataContext.Subjects
                .AsAsyncEnumerable();
        }

        public IAsyncEnumerable<Subject> GetAllSubjectUserIsAssignedTo(int id)
        {
            return _dataContext.Subjects
                .Where(subj =>
                subj.Users.Any(user => user.Id == id))
                .AsAsyncEnumerable();
        }

        public IAsyncEnumerable<Subject> GetAllSubjectUserIsUnAssignedTo(int id)
        {
            return _dataContext.Subjects
                .Where(subj =>
                    subj.Users.All(user => user.Id != id))
                .AsAsyncEnumerable();
        }

        public async Task<Subject> GetByIdAsync(int id)
        {
            return await _dataContext.Subjects
                .FirstOrDefaultAsync(subject => subject.Id == id);
        }

        public async Task<Subject> AddAsync(Subject subject)
        {
            await _dataContext.Subjects.AddAsync(subject);
            await _dataContext.SaveChangesAsync();

            return subject;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var existingSubject = await _dataContext.Subjects
                .FirstOrDefaultAsync(subject => subject.Id == id);

            if (existingSubject == null) return false;

            _dataContext.Subjects.Remove(existingSubject);
            await _dataContext.SaveChangesAsync();

            return true;
        }

        public async Task<Subject> UpdateAsync(Subject updatedSubject)
        {
            var existingSubject = await _dataContext.Subjects
                .Include(subject => subject.Users)
                .FirstOrDefaultAsync(subject => subject.Id == updatedSubject.Id);

            if (existingSubject == null) return null;

            _dataContext.Entry(existingSubject).CurrentValues.SetValues(updatedSubject);

            // find users and add them to subject
            existingSubject.Users.Clear();
            updatedSubject.Users.ForEach(subjectUser =>
                existingSubject.Users.Add(
                        _dataContext.Users.FirstOrDefaultAsync(user => user.Id == subjectUser.Id).Result
                        )
                );

            await _dataContext.SaveChangesAsync();

            return existingSubject;
        }
    }
}