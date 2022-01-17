using System.Collections.Generic;
using UDT.Model.Enums;

namespace UDT.Model.ViewModels
{
    public class SubjectCreationViewModel
    {
        public string Name { get; set; }
        public int Year { get; set; }
        public SubjectType Type { get; set; }
    }
}