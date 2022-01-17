using System;
using System.Collections.Generic;
using System.Text;
using UDT.Model.Enums;

namespace UDT.Model.ViewModels
{
    public class UserUpdateViewModel
    {
        public string Username { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public int Group { set; get; }
        public int Year { get; set; }
        public string Email { get; set; }
        public UserRole Role { get; set; }
        public int? Code { get; set; }
        public string ProfilePictureURL { get; set; }
        public DateTime DateOfBirth { get; set; }
        public List<int> Subjects { get; set; }
    }
}
