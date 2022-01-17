using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using UDT.Business.Interfaces;
using UDT.Model;
using UDT.Model.Entities;
using UDT.Model.Mappers;
using UDT.Repository;

namespace UDT.Business.Implementation
{
    public class AccountService : IAccountService
    {
        private readonly DataContext _context;
        private readonly AuthorizationSettings _authorizationSettings;

        public AccountService(DataContext context, IOptions<AuthorizationSettings> authorizationSettings)
        {
            _context = context;
            _authorizationSettings = authorizationSettings.Value;
        }

        public UDT.Model.AuthenticationResponse Authenticate(string username, string password)
        {
            User loggingInUser = _context.Users.FirstOrDefault(x => x.Username == username && x.Password == password);
            return new AuthenticationResponse()
            {
                Token = loggingInUser == null ? null : GenerateJwtToken(loggingInUser),
                User = loggingInUser.toViewModel()
            };
        }

        private string GenerateJwtToken(User user)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_authorizationSettings.Secret));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);
            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, user.Id.ToString()),
                new Claim("username", user.Username),
                new Claim("firstName", user.FirstName),
                new Claim("lastName", user.LastName),
            };

            var token = new JwtSecurityToken(
                issuer: _authorizationSettings.Issuer,
                audience: _authorizationSettings.Audience,
                claims: claims,
                expires: DateTime.Now.AddMinutes(30),
                signingCredentials: credentials
            );

            return new JwtSecurityTokenHandler().WriteToken(token);
        }
    }
}