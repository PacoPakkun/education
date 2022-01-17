using System.Linq;
using UDT.Model.Entities;
using UDT.Model.ViewModels;

namespace UDT.Model.Mappers
{
    public static class SubjectMappers
    {
        public static Subject ToEntity(this SubjectViewModel subjectViewModel)
        {
            var subject = new Subject
            {
                Id = subjectViewModel.Id,
                Name = subjectViewModel.Name,
                Type = subjectViewModel.Type,
                Year = subjectViewModel.Year,
                Users = subjectViewModel.Users?.Select(id => new User
                {
                    Id = id
                }).ToList()
            };

            return subject;
        }

        public static SubjectViewModel ToViewModel(this Subject subject)
        {
            var subjectViewModel = new SubjectViewModel
            {
                Id = subject.Id,
                Name = subject.Name,
                Type = subject.Type,
                Year = subject.Year,
                Users = subject.Users?.Select(user => user.Id).ToList()
            };

            return subjectViewModel;
        }

        public static Subject ToEntity(this SubjectCreationViewModel subjectCreationViewModel)
        {
            var subject = new Subject
            {
                Name = subjectCreationViewModel.Name,
                Type = subjectCreationViewModel.Type,
                Year = subjectCreationViewModel.Year
            };

            return subject;
        }

        public static Subject ToEntity(this SubjectUpdateViewModel subjectUpdateViewModel)
        {
            var subject = new Subject
            {
                Name = subjectUpdateViewModel.Name,
                Type = subjectUpdateViewModel.Type,
                Year = subjectUpdateViewModel.Year,
                Users = subjectUpdateViewModel.Users?.Select(id => new User
                {
                    Id = id
                }).ToList()
            };

            return subject;
        }
    }
}