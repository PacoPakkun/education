using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using UDT.Business.Interfaces;
using UDT.Model.Mappers;
using UDT.Model.ViewModels;
using UniversityDeadlineTracker.Filters;

namespace UniversityDeadlineTracker.Controllers
{
    [ApiController]
    [Route("api/usersTasks")]
    [AuthorizationFilter]
    public class UserTaskController : ControllerBase
    {
        private readonly IUserTaskService _usersTasksService;

        public UserTaskController(IUserTaskService usersTasksService)
        {
            _usersTasksService = usersTasksService;
        }

        [HttpGet]
        public IActionResult GetAll()
        {
            return Ok(_usersTasksService.GetAllAsync().Select(userTask => userTask.ToViewModel()));
        }

        [HttpGet]
        [Route("{id:int}")]
        public async Task<IActionResult> GetById([FromRoute] int id)
        {
            var userTask = await _usersTasksService.GetByIdAsync(id);

            if (userTask == null) return NotFound();

            return Ok(userTask.ToViewModel());
        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] UserTaskCreationViewModel userTaskCreationViewModel)
        {
            var userTask = await _usersTasksService.AddAsync(userTaskCreationViewModel.ToEntity());

            if (userTask == null) return NotFound();

            return Ok(userTask.ToViewModel());
        }

        [HttpDelete]
        [Route("{id:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Delete([FromRoute] int id)
        {
            var result = await _usersTasksService.DeleteAsync(id);

            if (!result) return NotFound();

            return Ok(true);
        }

        [HttpPut]
        [Route("{id:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Update([FromRoute] int id,
            [FromBody] UserTaskUpdateViewModel userTaskUpdateViewModel)
        {
            var userTask = userTaskUpdateViewModel.ToEntity();
            userTask.Id = id;

            userTask = await _usersTasksService.UpdateAsync(userTask);

            if (userTask == null) return NotFound();

            return Ok(userTask.ToViewModel());
        }
    }
}