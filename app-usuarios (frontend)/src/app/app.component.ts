import { Component, OnInit } from '@angular/core';
import { Usuario } from '../app/usuario';
import { UsuarioService } from '../app/usuario.service';
import { MessageService } from '../app/message.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  id: number;
  idSearch: number;
  usuario: Usuario;
  usuarioRes: Usuario;
  usuarios: Array<Usuario>;
  formUsuario: FormGroup;

  constructor(
    private usuarioService: UsuarioService,
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService
  ) {
    this.formUsuario = this.initFormUsuario();
  }

  ngOnInit(): void {
    this.listAll();
  }

  listAll() {
    this.usuarioService.getUsuarios().subscribe(data => this.usuarios = data)
  }

  insert() {
    this.usuarioService.setUsuario(this.formUsuario.value).subscribe(data => this.listAll())
    this.formUsuario = this.initFormUsuario();
    this.toastr.success('Operación exitosa', 'Usuario insertado')
  }

  update() {
    this.usuarioService.editUsuario(this.formUsuario.value, this.usuario.id).subscribe(data => this.listAll())
    this.formUsuario = this.initFormUsuario();
    this.clear();
    this.toastr.success('Operación exitosa', 'Usuario modificado')
  }

  insertOrUpdate() {
    if (this.id != null || this.id > 0) {
      this.update();
    } else {
      this.insert();
    }
  }

  onUpdate(usuario: Usuario) {
    this.usuario = usuario;
    this.id = usuario.id;
    this.formUsuario.setValue({ username: usuario.username, password: usuario.password, email: usuario.email, fnac: usuario.fnac })
  }

  onDelete(id: number) {
    if (confirm('¿Estas seguro de eliminar el usuario?')) {
      this.usuarioService.deleteUsuario(id).subscribe(data => this.listAll())
      this.toastr.success('Operación exitosa', 'Usuario eliminado')
    }
  }

  findById(id: number) {
    this.usuarioService.getUsuario(id).subscribe(data => this.usuarioRes = data as Usuario)
    this.idSearch = null;
    this.toastr.success('Operación exitosa', 'Usuario encontrado')
  }

  mostrarUsuario(){
    var div = document.getElementById('usuario');
    div.style.display = "block";
  }

  initFormUsuario(): FormGroup {
    return this.formBuilder.group({
      username: ['',Validators.required],
      password: ['',Validators.required],
      email: ['',[Validators.required, Validators.email]],
      fnac: ['',[Validators.required]]
    });
  }

  clear() {
    this.formUsuario = this.initFormUsuario();
    this.usuario = null;
    this.id = null;
  }

}
