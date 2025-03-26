package com.catfe.contextawaretesting.service;

import com.microsoft.z3.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymbolicExecutor {

    // Execute method based on metadata
    public void execute(String methodName, List<String> parameters, List<String> conditions) {
        try (Context ctx = new Context()) {
            Solver solver = ctx.mkSolver();

            // Create symbolic variables based on method parameters
            for (String param : parameters) {
                String[] parts = param.split(" ");
                String name = parts[0];
                String type = parts[1].replaceAll("[()]", "");

                if (type.equals("int")) {
                    IntExpr intExpr = ctx.mkIntConst(name);
                    solver.add(ctx.mkTrue());
                    // Add real constraints for intExpr if needed
                } else if (type.equals("double")) {
                    RealExpr realExpr = ctx.mkRealConst(name);
                    solver.add(ctx.mkTrue());
                    // Add real constraints for realExpr if needed
                }
                System.out.println(name);
            }

            // Apply execution paths based on conditions
            for (String condition : conditions) {
                System.out.println("Applying condition: " + condition);
                BoolExpr expr = ctx.parseSMTLIB2String("(assert " + condition + ")", null , null, null, null)[0];
                solver.add(expr);
            }

            // Check satisfiability (explore paths)
            if (solver.check() == Status.SATISFIABLE) {
                System.out.println("Path is reachable");
                System.out.println("Inputs: " + solver.getModel());
            } else {
                System.out.println("Path is NOT reachable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}