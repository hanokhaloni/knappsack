package com.sparc.knappsack.security;

import com.sparc.knappsack.components.services.ApplicationVersionService;
import com.sparc.knappsack.components.services.CategoryService;
import com.sparc.knappsack.components.services.GroupService;
import com.sparc.knappsack.components.services.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customMethodSecurityExpressionHandler")
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private ApplicationContext applicationContext;

    public CustomMethodSecurityExpressionHandler() {
        super();
    }

    /* (non-Javadoc)
      * @see org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler#createSecurityExpressionRoot(org.springframework.security.core.Authentication, org.aopalliance.intercept.MethodInvocation)
      */
    @Override
    protected SecurityExpressionRoot createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        GroupService groupService = (GroupService) applicationContext.getBean("groupService");
        root.setGroupService(groupService);
        UserService userService = (UserService) applicationContext.getBean("userService");
        root.setUserService(userService);
        ApplicationVersionService applicationVersionService = applicationContext.getBean(ApplicationVersionService.class);
        root.setApplicationVersionService(applicationVersionService);
        CategoryService categoryService = applicationContext.getBean(CategoryService.class);
        root.setCategoryService(categoryService);

        return root;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
       super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }
}
