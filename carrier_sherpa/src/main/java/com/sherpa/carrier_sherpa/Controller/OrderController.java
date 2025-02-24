package com.sherpa.carrier_sherpa.Controller;

import com.sherpa.carrier_sherpa.domain.entity.Order;
import com.sherpa.carrier_sherpa.domain.service.OrderService;
import com.sherpa.carrier_sherpa.dto.Luggage.LuggageResDto;
import com.sherpa.carrier_sherpa.dto.Member.MemberResDto;
import com.sherpa.carrier_sherpa.dto.Orders.DelieverReqDto;
import com.sherpa.carrier_sherpa.dto.Orders.OrderReqDto;
import com.sherpa.carrier_sherpa.dto.Orders.OrderResDto;
import com.sherpa.carrier_sherpa.dto.type.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orderId/{order}")
    public OrderResDto findById(
            HttpServletRequest httpServletRequest,
            @PathVariable("order") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.findById(memberResDto.getId(),orderId);
    }

    @GetMapping("/travelerId")
    public List<OrderResDto> findByTravelerId(
            HttpServletRequest httpServletRequest
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.findByTravelerId(memberResDto.getId());
    }

    @GetMapping("/deliverId")
    public List<OrderResDto> findByDeliverId(
            HttpServletRequest httpServletRequest
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.findByDeliverId(memberResDto.getId());
    }

    @GetMapping("/distance")
    public List<OrderResDto> findByDistance(
            HttpServletRequest httpServletRequest,
            @RequestParam("startLat") Double startLat,
            @RequestParam("startLng") Double startLng,
            @RequestParam("endLat") Double endLat,
            @RequestParam("endLng") Double endLng
    ) {
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.findByDistance(
                memberResDto.getId(),
                new DelieverReqDto(startLat, startLng, endLat, endLng)
        );
     }
    @PostMapping("")
    public OrderResDto create(
            HttpServletRequest httpServletRequest,
            @RequestBody OrderReqDto orderReqDto
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.create(memberResDto.getId(), orderReqDto);
    }

    @GetMapping("/isAddress")
    public String checkTerminal(
            @RequestParam("startLat") Double startLat,
            @RequestParam("startLng") Double startLng,
            @RequestParam("endLat") Double endLat,
            @RequestParam("endLng") Double endLng
    ){
        return orderService.checkTerminal(
                startLat,
                startLng,
                endLat,
                endLng);
        // 두 쪽 다 터미널에 있는 경우는 오류 날 수 있다.

        // if none 이면 프론트에서 cafe 관련 정보 삭제.
    }

    @PostMapping("acceptOrder/{orderid}")
    public OrderResDto accept(
            HttpServletRequest httpServletRequest,
            @PathVariable("orderid") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.accept(memberResDto.getId(),orderId);
    }
    @PatchMapping("updateOrder/{id}")
    public OrderResDto update(
            HttpServletRequest httpServletRequest,
            @RequestBody OrderReqDto orderReqDto,
            @PathVariable("id") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.update(memberResDto.getId(),orderId, orderReqDto);
    }

    // Order를 Delieve 완료.
    @PatchMapping("endOrder/{id}")
    public OrderResDto end(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.end(memberResDto.getId(), orderId);
    }

    // Order를 수락한 뒤 취소
    @PatchMapping("closeOrder/{id}")
    public OrderResDto close(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.close(memberResDto.getId(), orderId);
    }

    @DeleteMapping("deleteOrder/{orderId}")
    public OrderResDto delete(
            HttpServletRequest httpServletRequest,
            @PathVariable("orderId") String orderId
    ){
        HttpSession httpSession = httpServletRequest.getSession();
        MemberResDto memberResDto = (MemberResDto) httpSession.getAttribute("loginMember");
        return orderService.delete(memberResDto.getId(),orderId);
    }


}