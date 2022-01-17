using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using UDT.Model.Entities;
using UDT.Model.ViewModels;
using UDT.Business.Task;
using UniversityDeadlineTracker.Filters;

namespace UniversityDeadlineTracker.Controllers
{
    [ApiController]
    [Route("api/tasks")]
    [AuthorizationFilter]
    public class TaskController : ControllerBase
    {
        private readonly IServiceTask _taskService;

        private readonly ILogger<TaskController> _logger;

        public TaskController(ILogger<TaskController> logger, IServiceTask taskService)
        {
            _logger = logger;
            _taskService = taskService;
        }

        [HttpGet]
        public IActionResult GetAll()
        {
            return Ok(_taskService.GetAll());
        }

        [HttpGet("{taskId:int}")]
        public async Task<IActionResult> GetTaskById([FromRoute] int taskId)
        {
            var task = await _taskService.GetById(taskId);
            return Ok(task);
        }

        [HttpPost]
        public async Task<IActionResult> AddTask([FromBody] TaskCreationViewModel taskDto)
        {
            var dbTask = await _taskService.AddTask(taskDto);
            return Created(string.Empty, dbTask);
        }

        [HttpDelete("{taskId:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> DeleteTask([FromRoute] int taskId)
        {
            await _taskService.DeleteTask(taskId);
            return NoContent();
        }

        [HttpPut]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> EditTask([FromBody] TaskUpdateViewModel taskDto)
        {
            var dbTask = await _taskService.EditTask(taskDto);
            return Ok(dbTask);
        }
    }
}