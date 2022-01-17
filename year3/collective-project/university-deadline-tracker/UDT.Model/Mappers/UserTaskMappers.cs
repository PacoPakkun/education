using UDT.Model.Entities;
using UDT.Model.ViewModels;

namespace UDT.Model.Mappers
{
    public static class UserTaskMappers
    {
        public static UserTask ToEntity(this UserTaskViewModel userTaskViewModel)
        {
            var userTask = new UserTask
            {
                Id = userTaskViewModel.Id,
                TaskId = userTaskViewModel.TaskId,
                UserId = userTaskViewModel.UserId,
                Status = userTaskViewModel.Status,
                Content = userTaskViewModel.Content,
                Grade = userTaskViewModel.Grade
            };

            return userTask;
        }

        public static UserTaskViewModel ToViewModel(this UserTask userTask)
        {
            var userTaskViewModel = new UserTaskViewModel
            {
                Id = userTask.Id,
                TaskId = userTask.TaskId,
                UserId = userTask.UserId,
                Status = userTask.Status,
                Content = userTask.Content,
                Grade = userTask.Grade
            };

            return userTaskViewModel;
        }

        public static UserTask ToEntity(this UserTaskCreationViewModel userTaskCreationViewModel)
        {
            var userTask = new UserTask
            {
                TaskId = userTaskCreationViewModel.TaskId,
                UserId = userTaskCreationViewModel.UserId,
                Status = userTaskCreationViewModel.Status,
                Content = userTaskCreationViewModel.Content,
                Grade = userTaskCreationViewModel.Grade
            };

            return userTask;
        }

        public static UserTask ToEntity(this UserTaskUpdateViewModel userTaskUpdateViewModel)
        {
            var userTask = new UserTask
            {
                TaskId = userTaskUpdateViewModel.TaskId,
                UserId = userTaskUpdateViewModel.UserId,
                Status = userTaskUpdateViewModel.Status,
                Content = userTaskUpdateViewModel.Content,
                Grade = userTaskUpdateViewModel.Grade
            };

            return userTask;
        }
    }
}