import { CanActivateFn } from '@angular/router';

export const readGuard: CanActivateFn = (route, state) => {
  let permissions = JSON.parse(localStorage.getItem("permissions")!);

  if(permissions === null)
  {
    return false;
  }

  for(let i = 0;i<permissions.length;i++)
  {
    if(permissions[i].name == 'can_read_users')
    {
      return true;
    }
  }

  console.log(permissions);
  alert("You don't have permission to read users!");
  window.location.href = 'http://localhost:4200';
  return false;

};
