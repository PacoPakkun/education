using System;
using System.Collections.Generic;
using System.Text;

namespace UDT.Model.Entities
{
    public class Task
    {
        public int Id { get; set; }
        public string Title { set; get; }
        public string Subtitle { set; get; }
        public string Description { set; get; }
        public int SubjectId { set; get; }
        public Subject Subject { get; set; }
        public DateTime Deadline { set; get; }
        public double Penalty { get; set; }
    }
}
