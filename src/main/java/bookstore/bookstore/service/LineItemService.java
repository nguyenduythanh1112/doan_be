package bookstore.bookstore.service;

import bookstore.bookstore.model.BookItemModel;
import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.model.LineItemModel;
import bookstore.bookstore.repository.BookItemRepository;
import bookstore.bookstore.repository.CartRepository;
import bookstore.bookstore.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LineItemService {

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private BookItemRepository bookItemRepository;
    @Autowired
    private CartRepository cartRepository;

    public LineItemModel save(String username,int bookItemId) throws Exception {
        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null) throw new Exception("cart not found");
        BookItemModel bookItemModel=bookItemRepository.findById(bookItemId).get();
        if(bookItemModel==null) throw new Exception("book item not found");
        LineItemModel lineItemModel=lineItemRepository.findByCartIdAndBookItemId(cartModel.getId(),bookItemId);
        int remainQuantity=bookItemModel.getBookModel().getRemainQuantity();
        int quantity= lineItemModel!=null ? lineItemModel.getQuantity()+1 : 1;
        if(quantity>remainQuantity) throw new Exception("exceed quantity");
        if(lineItemModel!=null){
            lineItemModel.setQuantity(quantity);
            lineItemRepository.save(lineItemModel);
            return lineItemModel;
        }
        else{
            LineItemModel lim=new LineItemModel();
            lim.setQuantity(quantity);
            lim.setCartModel(cartModel);
            lim.setBookItemModel(bookItemModel);
            lineItemRepository.save(lim);
            return lim;
        }
    }

    public LineItemModel add(String username,int bookItemId) throws Exception {
        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null) throw new Exception("cart not found");
        BookItemModel bookItemModel=bookItemRepository.findById(bookItemId).get();
        if(bookItemModel==null) throw new Exception("book item not found");
        LineItemModel lineItemModel=lineItemRepository.findByCartIdAndBookItemId(cartModel.getId(),bookItemId);

        int remainQuantity=bookItemModel.getBookModel().getRemainQuantity();
        int quantity= lineItemModel!=null ? lineItemModel.getQuantity()+1 : 1;
        if(quantity>remainQuantity) throw new Exception("exceed quantity");

        if(lineItemModel!=null){
            lineItemModel.setQuantity(quantity);
            lineItemRepository.save(lineItemModel);
            return lineItemModel;
        }
        else{
            LineItemModel lim=new LineItemModel();
            lim.setQuantity(quantity);
            lim.setCartModel(cartModel);
            lim.setBookItemModel(bookItemModel);
            lineItemRepository.save(lim);
            return lim;
        }
    }

    public LineItemModel subtract(String username,int bookItemId) throws Exception {
        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null) throw new Exception("cart not found");
        BookItemModel bookItemModel=bookItemRepository.findById(bookItemId).get();
        if(bookItemModel==null) throw new Exception("book item not found");
        LineItemModel lineItemModel=lineItemRepository.findByCartIdAndBookItemId(cartModel.getId(),bookItemId);
        int quantity= lineItemModel.getQuantity()-1;
        if(quantity<=0) throw new Exception("quantity not less than 0");
        lineItemModel.setQuantity(quantity);
        lineItemRepository.save(lineItemModel);
        return lineItemModel;
    }

    public List<LineItemModel> findByUsername(String username){
        return lineItemRepository.findByUsername(username);
    }

    public List<LineItemModel> findByCartId(int cartId){

        List<LineItemModel> lineItemModels = lineItemRepository.findByCartId(cartId);
        List<LineItemModel> lineItemModelResults=new ArrayList<>();

        for(LineItemModel lineItemModel:lineItemModels){
            int remainQuantity=lineItemModel.getBookItemModel().getBookModel().getRemainQuantity();
            if(lineItemModel.getQuantity()>remainQuantity){
                lineItemModel.setQuantity(remainQuantity);
                lineItemRepository.save(lineItemModel);
            }
            lineItemModelResults.add(lineItemModel);
        }
        return lineItemModelResults;
    }

    public void delete(String username,int bookItemId) throws Exception{
        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null) throw new Exception("cart not found");
        LineItemModel lineItemModel=lineItemRepository.findByCartIdAndBookItemId(cartModel.getId(),bookItemId);
        if(lineItemModel==null) throw new Exception("line item not found");
        lineItemModel.setCartModel(null);
        lineItemRepository.save(lineItemModel);
    }

}
