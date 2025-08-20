package store.service;

import java.util.List;
import store.domain.Product;

public class Inventory {
    private List<Product> products;

    public Inventory(List<Product> products) {
        this.products = products;
    }

    /*
    상품 이름과 구매 수량을 인자로 받음 -> products에서 상품 이름으로 상품 조회
    -> 프로모션 상품 + 일반 상품 / 일반 상품 / 프로모션 상품 / 빈 리스트로 반환 가능
    -> 빈 리스트: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
    -> 프로모션 상품: 프로모션 상품 개수에 충족하는지 확인(T/F)
        -> 충족하지 않다면 추가 여부 확인(추가 수량까지 충분한지 확인)
        -> 추가 시 충분하지 않다면 바로 일반 결제 여부 받기
    -> 재고 충분한지 확인(T/F)
    -> F -> 일반 결제 여부 받기 -> 일반 상품 단계 진행
    -> T -> 프로모션 할인 금액 반환
    -> 일반 상품: 재고 충분한지 확인(T/F)  -> true 반환 + 재고 업데이트
    -> 프로모션 상품 + 일반 상품: 프로모션 상품 단계로 진행
    */


}
