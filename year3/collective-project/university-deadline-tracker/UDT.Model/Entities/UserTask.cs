using System;
using System.Collections.Generic;
using System.Text;
using UDT.Model.Enums;

namespace UDT.Model.Entities
{
    public class UserTask
    {
        public int Id { get; set; }
        public int TaskId { get; set; }
        public Task Task { get; set; }
        public int UserId { get; set; }
        public User User { get; set; }
        public TaskStatus Status { get; set; }
        public string Content { get; set; }
        public double Grade { get; set; }
    }
}
