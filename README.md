# FuncionalMapper
 Snippet classes that allow to set properties using JDK8 functional programming, avoiding NullpointerException

# Use

       mapperUtils.map(dto::setProjectName,   //setter
                entity::getEmployees,         //getter
                ListUtils::getFirstValue,     //function that accepts getter result (if null not apply)
                Employee::getProjects,        //function that accepts getter result
                ListUtils::getFirstValue,     //function that accepts getter result
                Project::getName);            //function that accepts getter result
                
                
prevent you from using

     List<Employee> employees = entity.getEmployees();          //entity::getEmployees,
        if(employees!=null && employees.size()>0){
            Employee employee = employees.get(0);               //ListUtils::getFirstValue
            List<Project> projects = employee.getProjects();    //Employee::getProjects
            if(projects!=null && projects.size()>0){
                Project project = projects.get(0);              //ListUtils::getFirstValue
                if(project!=null){
                    String projectName = project.getName();     //Project::getName
                    dto.setProjectName(projectName);            //setter
                }
        }
        
        
  # Other uses
         Integer idAccount = mapperUtils.getNullSafe(entity::getUser,
                User::getAccount,
                Account::getId);
                
   vs
        
        if(entity.getUser()!=null){
            Account account = entity.getUser().getAccount();
            if(account!=null){
                Integer idAccount = account.getId();
            }
        }
  
