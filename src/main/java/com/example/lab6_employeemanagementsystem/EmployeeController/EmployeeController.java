package com.example.lab6_employeemanagementsystem.EmployeeController;


import com.example.lab6_employeemanagementsystem.ApiResponse.ApiResponse;
import com.example.lab6_employeemanagementsystem.Model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee-management-system")
public class EmployeeController {

     ArrayList<Employee> employees = new ArrayList<>();
    // CRUD endpoint

    @GetMapping("/get")
    public ResponseEntity getEmployees(){
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        for(Employee em : employees ){
            if(em.getID().equalsIgnoreCase(employee.getID()))
                return ResponseEntity.status(400).body(new ApiResponse("Employee already exists"));
        }

        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));

    }


    @PutMapping("/update")
    public ResponseEntity updateEmployee(@RequestBody @Valid Employee employee,Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        for (Employee em : employees){
            if (em.getID().equalsIgnoreCase(employee.getID())) {
                em = employee;
                return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
            }
        }

        return ResponseEntity.status(400).body("Employee does not exists ");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id){

        for (Employee employee : employees){

            if (employee.getID().equalsIgnoreCase(id)){
                employees.remove(employee);
                return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
            }
        }

        return ResponseEntity.status(400).body(new ApiResponse("Employee not exists"));
    }

    // ___________________________________End____________________________________________

    @GetMapping("/search-by-Position/{position}")
    public ResponseEntity searchByPosition(@PathVariable String position){

        //supervisor or coordinator
        if (position.equalsIgnoreCase("supervisor") || position.equalsIgnoreCase("coordinator")){
            ArrayList<Employee> tem = new ArrayList<>();

            for (Employee em : employees)
                if (em.getPosition().equalsIgnoreCase(position))
                    tem.add(em);


            return ResponseEntity.status(200).body(tem);
        }


        return ResponseEntity.status(400).body(new ApiResponse(""));

    }


    @GetMapping("/employee-by-age/{minAge}/{maxAge}")
    public ResponseEntity getEmployeeByAgeRange(@PathVariable  int minAge,@PathVariable int maxAge){

        ArrayList<Employee> tem  =new ArrayList<>();
        for (Employee em : employees)
            if (em.getAge()>=minAge && em.getAge() <= maxAge)
                tem.add(em);



        return ResponseEntity.status(200).body(tem);
    }

    @PutMapping("/leave-Request/{id}")
    public ResponseEntity leaveRequest(@PathVariable  String id){

        for (Employee employee : employees){

            if (employee.getID().equalsIgnoreCase(id) && !employee.isOnLeave() && employee.getAnnualLeave() >=1){

                employee.setAnnualLeave(employee.getAnnualLeave()-1);
                employee.setOnLeave(true);
                return ResponseEntity.status(200).body(new ApiResponse("Employee is on leave, remain is "+ employee.getAnnualLeave()));



            }
        }

        return ResponseEntity.status(400).body(new ApiResponse("Employee not exists"));
    }

    @GetMapping("/employee-with-noleave")
    public ResponseEntity employeeWithNoLeave(){

        ArrayList<Employee> tem  =new ArrayList<>();
        for (Employee em : employees)
            if(em.getAnnualLeave() ==0){
                tem.add(em);
            }



        return ResponseEntity.status(200).body(tem);
    }

    @PutMapping("/promote-employee/{id}/{target_id}")
    public ResponseEntity promoteEmployee(@PathVariable String id,@PathVariable String target_id){

        for (Employee supervisor : employees) {

            if (supervisor.getID().equalsIgnoreCase(id) && supervisor.getPosition().equalsIgnoreCase("supervisor")) {

                for (Employee coordinator : employees) {

                    if (coordinator.getID().equalsIgnoreCase(target_id) && !coordinator.isOnLeave() && coordinator.getAge() >= 30) {
                        coordinator.setPosition("supervisor");
                        return ResponseEntity.status(200).body(new ApiResponse("Requested was approve. " + coordinator.getName() + " has been promoted"));
                    }
                }
            }
        }


        return ResponseEntity.status(200).body(new ApiResponse("Requested was rejected. Contact with your admin"));
    }

}
