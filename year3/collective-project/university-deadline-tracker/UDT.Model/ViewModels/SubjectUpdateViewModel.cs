using System.Collections.Generic;
using UDT.Model.Enums;

namespace UDT.Model.ViewModels
{
    public class SubjectUpdateViewModel
    {
        public string Name { get; set; }
        public SubjectType Type { get; set; }
        public int Year { get; set; }
        public List<int> Users { get; set; }
    }
}