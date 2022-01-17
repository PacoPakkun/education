using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using UDT.Business.Interfaces;
using UDT.Model.Mappers;
using UDT.Model.ViewModels;
using UniversityDeadlineTracker.Filters;

namespace UniversityDeadlineTracker.Controllers
{
    [ApiController]
    [Route("api/subjects")]
    [AuthorizationFilter]
    public class SubjectController : ControllerBase
    {
        private readonly ISubjectService _subjectService;

        public SubjectController(ISubjectService subjectService)
        {
            _subjectService = subjectService;
        }

        [HttpGet]
        public IActionResult GetAll()
        {
            return Ok(_subjectService.GetAllAsync());
        }

        [HttpGet]
        [Route("{id:int}")]
        public async Task<IActionResult> GetById([FromRoute] int id)
        {
            var subject = await _subjectService.GetByIdAsync(id);

            if (subject == null) return NotFound();
            
            return Ok(subject.ToViewModel());
        }

        [HttpPost]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Add([FromBody] SubjectCreationViewModel subjectCreationViewModel)
        {
            var subject = await _subjectService.AddAsync(subjectCreationViewModel.ToEntity());

            if (subject == null) return NotFound();

            return Ok(subject.ToViewModel());
        }

        [HttpDelete]
        [Route("{id:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Delete([FromRoute] int id)
        {
            var result = await _subjectService.DeleteAsync(id);

            if (!result) return NotFound();

            return Ok(true);
        }

        [HttpPut]
        [Route("{id:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Update([FromRoute] int id,
            [FromBody] SubjectUpdateViewModel subjectUpdateViewModel)
        {
            var subject = subjectUpdateViewModel.ToEntity();
            subject.Id = id;

            try
            {
                subject = await _subjectService.UpdateAsync(subject);
            }
            catch (ArgumentNullException)
            {
                return NotFound("An user you tried to add does not exist!");
            }

            if (subject == null) return NotFound();

            return Ok(subject.ToViewModel());
        }

        [HttpGet]
        [Route("assigned/{id:int}")]
        public IActionResult GetAllSubjectUserIsAssigned([FromRoute] int id)
        {
            return Ok(_subjectService.GetAllSubjectUserIsAssignedTo(id));
        }

        [HttpGet]
        [Route("notAssigned/{id:int}")]
        public IActionResult GetAllSubjectUserIsNotAssigned([FromRoute] int id)
        {
            return Ok(_subjectService.GetAllSubjectUserIsUnAssignedTo(id));
        }
    }
}