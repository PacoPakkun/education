using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UDT.Model.Entities;
using UDT.Model.ViewModels;

namespace UDT.Model.Mappers
{
    public static class UserMappers
    {

        public static User toEntity(this UserViewModel userViewModel)
        {
            User user = new User
            {
                Id = userViewModel.Id,
                Username = userViewModel.Username,
                FirstName = userViewModel.FirstName,
                LastName = userViewModel.LastName,
                Group = userViewModel.Group,
                Year = userViewModel.Year,
                Email = userViewModel.Email,
                Role = userViewModel.Role,
                Code = userViewModel.Code,
                ProfilePictureURL = userViewModel.ProfilePictureURL,
                DateOfBirth = userViewModel.DateOfBirth,
                Subjects = userViewModel.Subjects?.Select(id => new Subject {
                    Id = id
                }).ToList(),
            };

            return user;
        }

        public static UserViewModel toViewModel(this User user)
        {
            UserViewModel userViewModel = user != null ? new UserViewModel
            {
                Id = user.Id,
                Username = user.Username,
                FirstName = user.FirstName,
                LastName = user.LastName,
                Group = user.Group,
                Year = user.Year,
                Email = user.Email,
                Role = user.Role,
                Code = user.Code,
                ProfilePictureURL = user.ProfilePictureURL,
                DateOfBirth = user.DateOfBirth,

                Subjects = user.Subjects?.Select(subject => subject.Id).ToList(),
            } : null;

            return userViewModel;
        }

        public static User toEntity(this UserUpdateViewModel userUpdateViewModel)
        {
            User user = new User
            {
                Username = userUpdateViewModel.Username,
                FirstName = userUpdateViewModel.FirstName,
                LastName = userUpdateViewModel.LastName,
                Group = userUpdateViewModel.Group,
                Year = userUpdateViewModel.Year,
                Email = userUpdateViewModel.Email,
                Role = userUpdateViewModel.Role,
                Code = userUpdateViewModel.Code,
                ProfilePictureURL = userUpdateViewModel.ProfilePictureURL,
                DateOfBirth = userUpdateViewModel.DateOfBirth,
                Subjects = userUpdateViewModel.Subjects?.Select(id => new Subject
                {
                    Id = id
                }).ToList(),
            };

            return user;
        }

        public static User toEntity(this UserCreationViewModel userCreationViewModel)
        {
            User user = new User
            {
                Username = userCreationViewModel.Username,
                Password = userCreationViewModel.Password,
                FirstName = userCreationViewModel.FirstName,
                LastName = userCreationViewModel.LastName,
                Group = userCreationViewModel.Group,
                Year = userCreationViewModel.Year,
                Email = userCreationViewModel.Email,
                Role = userCreationViewModel.Role,
                Code = userCreationViewModel.Code,
                ProfilePictureURL = userCreationViewModel.ProfilePictureURL,
                DateOfBirth = userCreationViewModel.DateOfBirth
            };

            return user;
        }

    }
}
