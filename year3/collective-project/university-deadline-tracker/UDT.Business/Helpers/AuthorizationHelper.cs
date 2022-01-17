using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UDT.Business.Interfaces;
using UDT.Model;
using UDT.Model.Entities;

namespace UDT.Business.Helpers
{
	public class AuthorizationHelper : IAuthorizationHelper
	{
		private readonly AuthorizationSettings _authorizationSettings;
		private readonly IUserService _userService;

		public AuthorizationHelper(IOptions<AuthorizationSettings> authorizationSettings, IUserService userService)
		{
			_authorizationSettings = authorizationSettings.Value;
			_userService = userService;
		}

		public bool IsTokenValid(string token)
		{
			SecurityToken validatedToken;
			try
			{
				var tokenHandler = new JwtSecurityTokenHandler();
				var secretKey = Encoding.ASCII.GetBytes(_authorizationSettings.Secret);

				tokenHandler.ValidateToken(token, new TokenValidationParameters
				{
					ValidateLifetime = true,
					ValidateIssuer = true,
					ValidateAudience = true,
					ValidateIssuerSigningKey = true,

					ValidIssuer = _authorizationSettings.Issuer,
					ValidAudience = _authorizationSettings.Audience,
					IssuerSigningKey = new SymmetricSecurityKey(secretKey),
				}
				, out validatedToken);
			}
			catch
			{
				return false;
			}

			return true;
		}

		public async Task<bool> IsUsersRoleAuthorized(string token, string allowedRoles)
        {
			var tokenHandler = new JwtSecurityTokenHandler();
			var jwtToken = (JwtSecurityToken)tokenHandler.ReadToken(token);

			var userId = int.Parse(jwtToken.Subject);
			var user = await _userService.GetByIDAsync(userId);

			return allowedRoles.Split(",").Any(role => role.Equals(user.Role.ToString()));
        }
	}
}
