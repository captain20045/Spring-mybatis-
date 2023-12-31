package com.project04.controller;

import org.springframework.web.bind.annotation.*;

import com.project04.domain.UserRoute;
import com.project04.service.UserRouteService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // 세션을 위해 credentials 허용
@RestController
@RequestMapping("/api")
public class UserRouteController {

    @Autowired
    private UserRouteService userrouteService;
    
    @PostMapping("/user-save")
    public ResponseEntity<?> registerUserRoute(@RequestBody UserRoute route) {
        boolean result = userrouteService.addRoute(route);
        if (result) {
            return ResponseEntity.ok().body(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }
    
    @GetMapping("/user/routes/{userid}")
    public ResponseEntity<?> getUserRoutes(@PathVariable("userid") String userid) {
        List<UserRoute> routes = userrouteService.getUserRoutes(userid); // 메서드 이름 및 로직 변경
        if (routes != null && !routes.isEmpty()) {
            return ResponseEntity.ok(routes); // 리스트 반환
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/route-delete/{routeId}/{routeSeq}")
    public ResponseEntity<?> deleteUserRoute(@PathVariable String routeId, @PathVariable int routeSeq) {
        UserRoute route_d = new UserRoute(); // UserRoute 인스턴스 생성
        route_d.setUserid(routeId);
        route_d.setRoute_seq(routeSeq);

        boolean isDeleted = userrouteService.deleteUserRoute(route_d);
        if (isDeleted) {
            return ResponseEntity.ok().body(Map.of("message", "삭제완료"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to delete user route"));
        }
    }

}
