package com.example.controller;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    private BookRepository repository;

    /*
    * CRUD
    * Create
    * Retrieve/Read
    * Update
    * Delete
    * */


    //para obtener el LISTADO DE LIBROS

/*Accediendo al controlador del metodo index accederiamos a la raiz principal del directorio del programa
* Nos devuelve el controlador "books" y desde este segundo controlador accede al metodo findAll y nos devuelve
* todos los libros del BookRepository y asi movernos a la pantalla o vista del "book-list.thml"*/


    @GetMapping("/")
    public String index(){
        return "redirect:/books";
    }

    //CONTROLADORES PARA LA CREACION DE UN LIBRO , LA EDICION DE UN LIBRO,LA VISUALIZACION DE UN LIBRO Y EL BORRADO DE UN LIBRO



    //R----------controlador para recuperar todos los libros---------------------------

    @GetMapping("/books")
    public String findAll(Model model){
        model.addAttribute("books", repository.findAll());
        return "book-list";
    }



    //R-----------controlador para buscar por ID--------------------------------------
    @GetMapping("/books/view/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("book", repository.findById(id).get());
        return "book-view";
    }


    //--------------METODO PARA QUE NOS LLEVE AL FORMULARIO VACIO para crear, da el formulario al usuario------------------
    @GetMapping("/books/form")
    public String getEmptyForm(Model model){
        model.addAttribute("book", new Book()); //este libro es el que se va a rellenar en el fron , es de cir en el thymeleaf
        return "book-form";
    }

    //--------------METODO PARA QUE NOS LLEVE AL FORMULARIO relleno Y PODER EDITAR UN LIBRO ----------------
    @GetMapping("/books/edit{id}")
    public String getFormWithBook(Model model, @PathVariable Long id){
        //si existe el libro, cargamelo y llevame a esa pantalla
        if(repository.existsById(id)){
            repository.findById(id).ifPresent(b -> model.addAttribute("book",b ));
            return "book-form";
        }    else
            return "redirect:/books/form"; //si no existe que nos lleve al formulario vacio

    }


    //C---U----------controlador para la CREACION  y ACTUALIZACION de datos----------------------------------
    //@ModelAttribute para ver que viene de un formulario
    //el redirect nos va a llevar despues de crear el libro al conrolador /books inicial findAll y nos mostrara todos los libros

   //este recibe el formulario del usuario
    @PostMapping("/books")
    public String create (@ModelAttribute Book book){
        if(book.getId() != null) {
            //actualizacion
            repository.findById(book.getId()).ifPresent( b -> {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPrice(book.getPrice());
                repository.save(b);
            });
        } else {
            //creación
            repository.save(book);

        }
        return "redirect:/books";
    }


    //D--------Controlador para borrar datos
    @GetMapping("/books/delete/{id}")
    public String deleteById(@PathVariable Long id){
        if(repository.existsById(id))
            repository.deleteById(id);
        return "redirect:/books";
    }



}
