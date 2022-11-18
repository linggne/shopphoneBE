package com.shopPhone.shopphoneBE.service;

import com.shopPhone.shopphoneBE.model.CartDetailRequest;

public interface ICartDetailService {
    public Boolean createCartDetail(CartDetailRequest cartDetailRequest);
    public Boolean deleteCartDetail(int cartDetailId);
    public Boolean removeOneCountCartDetail(int cartDetailId);
}
