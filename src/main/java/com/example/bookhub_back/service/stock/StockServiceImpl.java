package com.example.bookhub_back.service.stock;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessage;
import com.example.bookhub_back.common.enums.StockActionType;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.stock.request.StockUpdateRequestDto;
import com.example.bookhub_back.dto.stock.response.StockResponseDto;
import com.example.bookhub_back.entity.Stock;
import com.example.bookhub_back.entity.StockLog;
import com.example.bookhub_back.repository.BookRepository;
import com.example.bookhub_back.repository.EmployeeRepository;
import com.example.bookhub_back.repository.stock.StockLogRepository;
import com.example.bookhub_back.repository.stock.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

//    private final StockRepository stockRepository;
//    private final StockLogRepository stockLogRepository;
//    private final AlertService alertService;
//    private final AuthorityRepository authorityRepository;
//    private final EmployeeRepository employeeRepository;
//    private final BookRepository bookRepository;
//    private final BranchRepository branchRepository;


    @Override
    public ResponseDto<Void> updateStock(Long stockId, StockUpdateRequestDto dto) {
//        StockActionType actionType = StockActionType.valueOf(dto.getType().toUpperCase());
//        Long updatedAmount;
//
//        Stock stock = stockRepository.findById(stockId).orElseThrow(()-> new EntityNotFoundException("해당 재고 ID가 존재하지 않습니다."));
//
//
//        switch (actionType) {
//            case IN -> updatedAmount = stock.getBookAmount() + dto.getAmount();
//            case OUT, LOSS -> {
//                if(stock.getBookAmount() < dto.getAmount()){
//                    throw new IllegalArgumentException("재고부족");
//                }
//                updatedAmount = stock.getBookAmount() - dto.getAmount();
//            }
//            default -> throw new IllegalArgumentException("잘못된 타입");
//
//        }
//        stock.setBookAmount(updatedAmount);
//        stockRepository.save(stock);
//
//        StockLog stockLog = StockLog.builder()
//                .stockActionType(StockActionType.valueOf(dto.getType().toUpperCase()))
//                .employeeId(employeeRepository.findById(dto.getEmployeeId().orElsethrow(()->new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND))))
//                .bookIsbn(bookRepository.findById(dto.getBookIsbn()).orElsethrow(()->new IllegalArgumentException(ResponseMessage.RESOURCE_NOT_FOUND))))
//                .branchId(employeeRepository.findById(dto.getEmployeeId().getBranchId().orElsethrow(()->new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND))))
//                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PageResponseDto<StockResponseDto>> getFilteredStocks(int page, int size, Long branchId, String bookTitle) {
        return null;
    }
}
