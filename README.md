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

     List<Employee> employees = entity.getEmployees();
        if(employees!=null && employees.size()>0){
            Employee employee = employees.get(0);
            List<Project> projects = expediente.getProjects();
            if(projects!=null && projects.size()>0){
                Project project = projects.get(0); 
                if(project!=null){
                    String projectName = project.getName();
                    dto.setProjectName(projectName);
                }
        }
        
        
  # Other uses
         Integer idAccount = mapperUtils.getNullSafe(entity::getUser,
                User::getAccount,
                Account::getId);
                
        //vs
        
        if(entity.getUser()!=null){
            Account account = entity.getUser().getAccount();
            if(account!=null){
                Integer idAccount = account.getId();
            }
        }
  
