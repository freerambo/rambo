package com.eri.ict.service.account.realm;

import java.io.Serializable;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eri.ict.entity.User;
import com.eri.ict.service.account.AccountService;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.google.common.base.Objects;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-13
 * <p>
 * Version: 1.0
 */
public class MyCasRealm extends CasRealm {
	static final Logger log = LoggerFactory.getLogger(MyCasRealm.class);

	protected AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		log.info(username + " In Authrization");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// authorizationInfo.setRoles(userService.findRoles(username));
		// authorizationInfo.setStringPermissions(userService.findPermissions(username));
		User user = accountService.findUserByLoginName(username);
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return authorizationInfo;
	}
	
	
	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;

		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
}
