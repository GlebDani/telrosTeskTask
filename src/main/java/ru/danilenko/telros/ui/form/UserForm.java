package ru.danilenko.telros.ui.form;


import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import ru.danilenko.telros.backend.emuns.Roles;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.UserService;



public class UserForm extends VerticalLayout {

    private Binder<User> binder ;
    private  UserService userService;

    Binder<User> binderEmail = new Binder<>();


    private EmailField email= new EmailField("Email");
    private PasswordField password = new PasswordField("Пароль");
    private TextField surname = new TextField("Фамилия");
    private TextField name = new TextField("Имя");
    private TextField fatherName = new TextField("Отчество");

    private DatePicker dateOfBirth = new DatePicker("Дата рождения");
    private TextField phoneNumber = new TextField("Номер телефона");
    private ComboBox<Roles> role = new ComboBox<>("Роль");
//    private Avatar avatarField = new Avatar("Image");


    public UserForm(  UserService userService){
        binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(this);
        binderEmail.forField(email).withValidator(w -> userService.loadUserByEmail(w).isEmpty(),"уже тут").bind(User::getEmail,User::setEmail);
        this.userService = userService;

        role.setItems(Roles.values());
        add(
//                avatarField,
                email,
                new HorizontalLayout(surname,
                        name),
                new HorizontalLayout(fatherName,
                        dateOfBirth),
                new HorizontalLayout(phoneNumber,
                        role)


        );
        setUser(new User());
    }

    public boolean validateToUpdate(String email1) {
        boolean sameEmail = true;
        if(!email1.equals(getUser().getEmail())) {
            binderEmail.validate().getBeanValidationResults();
            sameEmail = binderEmail.isValid();
        }
        binder.validate().getBeanValidationResults();
        return binder.isValid() && (sameEmail);
    }

    public boolean validateToAdd() {
        binder.validate().getBeanValidationResults();
        binderEmail.validate().getBeanValidationResults();
        System.out.println("vta1"+ (binder.isValid()));
        System.out.println("vta2"+ (binderEmail.isValid()));
        return binder.isValid() && binderEmail.isValid();
    }


    public void setUser(User user)  {
        binder.setBean(user);

    }

    public User getUser(){
        return binder.getBean();
    }
    public void disable(){
        binder.setReadOnly(true);
    }

}
