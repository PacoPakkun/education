

using UDT.Model.Enums;

namespace UDT.Model.ViewModels
{
    public class UserTaskViewModel
    {
        public int Id { get; set; }
        public int TaskId { get; set; }
        public int UserId { get; set; }
        public TaskStatus Status { get; set; }
        public string Content { get; set; }
        public double Grade { get; set; }
    }
}