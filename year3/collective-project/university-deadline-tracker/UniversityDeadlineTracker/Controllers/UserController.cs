using System;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using UDT.Business.Interfaces;
using UDT.Model.Entities;
using UDT.Model.ViewModels;
using UDT.Model.Mappers;
using System.Linq;
using UniversityDeadlineTracker.Filters;
using System.Collections.Generic;

namespace UniversityDeadlineTracker.Controllers
{
    [ApiController]
    [Route("api/users")]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        private readonly IService _service;

        public UserController(IUserService userService, IService service)
        {
            _userService = userService;
            _service = service;
        }

        [HttpGet]
        [Route("{id:int}")]
        [AuthorizationFilter]
        public async Task<IActionResult> GetByIdAsync([FromRoute] int id)
        {
            var user = await _userService.GetByIDAsync(id);
            if (user == null)
                return NotFound();

            return Ok(
                user.toViewModel()
            );
        }

        [HttpGet]
        [AuthorizationFilter]
        public async Task<IActionResult> GetAll()
        {
            return Ok(
                (await _userService.GetAllAsync()).Select(user => user.toViewModel())
            );
        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] UserCreationViewModel userCreationViewModel)
        {
            return Ok(
                (await _userService.AddAsync(
                    userCreationViewModel.toEntity())
                ).toViewModel()
            );
        }

        [HttpPut]
        [Route("{id:int}")]
        [AuthorizationFilter]
        public async Task<IActionResult> Update([FromRoute] int id, [FromBody] UserUpdateViewModel userUpdateViewModel)
        {
            User user = userUpdateViewModel.toEntity();
            user.Id = id;

            try
            {
                user = await _userService.UpdateAsync(user);
            }
            catch (ArgumentNullException)
            {
                return NotFound("A subject you tried to add does not exist!");
            }

            if (user == null) return NotFound();

            return Ok(user.toViewModel());
        }

        [HttpDelete]
        [Route("{id:int}")]
        [AuthorizationFilter(roles: "Teacher")]
        public async Task<IActionResult> Delete([FromRoute] int id)
        {
            return Ok(await _userService.DeleteAsync(id));
        }


        [HttpGet]
        [Route("usertasks/{userId:int}")]
        [AuthorizationFilter]
        public async Task<IActionResult> GetTasksForUser([FromRoute] int userId)
        {
            return Ok((await _service.getUserTasksForUser(userId)).Select(usertask => usertask.ToViewModel()));
        }

        [HttpPost]
        [Route("{userId:int}/addsubject/{subjectId:int}")]
        [AuthorizationFilter]
        public async Task<IActionResult> EnrollUserToSubject([FromRoute] int subjectId, [FromRoute] int userId)
        {
            try
            {
                User user = await _service.enrollUserToSubject(userId, subjectId);
                return Ok(user.toViewModel());
            }
            catch (Exception e)
            {
                return NotFound(e.ToString());
            }
        }
    }
}