import { CanActivateFn } from '@angular/router';

export const updateGuard: CanActivateFn = (route, state) => {
  let permissions = JSON.parse(localStorage.getItem("permissions")!);

  if(permissions === null)
  {
    return false;
  }

  for(let i = 0;i<permissions.length;i++)
  {
    if(permissions[i].name == 'can_update_users')
    {
      return true;
    }
  }

  console.log(permissions);
  alert("You don't have permission to update a user!");
  window.location.href = 'http://localhost:4200';
  return false;
};
