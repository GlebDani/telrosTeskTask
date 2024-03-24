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

    /**
     * email binder to check if exist in db
     */
    Binder<User> binderEmail = new Binder<>();

    /**
     * User field to bind
     */
    protected EmailField email= new EmailField("Email");
    protected PasswordField password = new PasswordField("Пароль");
    protected TextField surname = new TextField("Фамилия");
    protected TextField name = new TextField("Имя");
    protected TextField fatherName = new TextField("Отчество");
    protected DatePicker dateOfBirth = new DatePicker("Дата рождения");
    protected TextField phoneNumber = new TextField("Номер телефона");
    protected ComboBox<Roles> role = new ComboBox<>("Роль");

    public UserForm(  UserService userService){
        binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(this);
        binderEmail.forField(email).withValidator(w -> userService.loadUserByEmail(w).isEmpty(),"уже тут").bind(User::getEmail,User::setEmail);
        this.userService = userService;

        role.setItems(Roles.values());
        add(
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

    /**
     * check if user's to update email is the same as it was before.
     * if emails coincide then we validate other fields
     * if emails are different we first check if new email is possible to save
     * @param email1 initial email of user
     * @return true if possible to save
     */
    public boolean validateToUpdate(String email1) {
        boolean sameEmail = true;
        if(!email1.equals(getUser().getEmail())) {
            binderEmail.validate().getBeanValidationResults();
            sameEmail = binderEmail.isValid();
        }
        binder.validate().getBeanValidationResults();
        return binder.isValid() && (sameEmail);
    }
    /**
     * check if user's to SAVE IS VALID.
     */
    public boolean validateToAdd() {
        binder.validate().getBeanValidationResults();
        binderEmail.validate().getBeanValidationResults();
        return binder.isValid() && binderEmail.isValid();
    }

    /**
     * set user to form
     * @param user user
     */
    public void setUser(User user)  {
        binder.setBean(user);
    }

    /**
     * get user from Form
     * @return User
     */
    public User getUser(){
        return binder.getBean();
    }

    /**
     * set read only mode
     */
    public void disable(){
        binder.setReadOnly(true);
    }

}
